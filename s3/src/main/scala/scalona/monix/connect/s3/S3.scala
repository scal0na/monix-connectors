package scalona.monix.connect.s3

import java.nio.ByteBuffer
import java.util.concurrent.CompletableFuture

import monix.reactive.{ Consumer, Observable, Observer }
import monix.execution.{ Ack, Scheduler }
import monix.eval.Task
import software.amazon.awssdk.core.async.{ AsyncRequestBody, AsyncResponseTransformer, SdkPublisher }
import software.amazon.awssdk.services.s3.S3AsyncClient
import software.amazon.awssdk.services.s3.model.{ GetObjectAclRequest, GetObjectAclResponse, GetObjectRequest, GetObjectResponse, PutObjectRequest, PutObjectResponse }

import scala.jdk.FutureConverters._

object S3 {

  //todo delimit content type to enum
  def putObject(
    bucketName: String,
    key: String,
    content: ByteBuffer,
    contentLength: Option[Long] = None,
    contentType: Option[String] = None)(implicit s3Client: S3AsyncClient, s: Scheduler): Task[PutObjectResponse] = {
    val contentLenght: Long = contentLength.getOrElse(content.array().length.toLong)
    val putObjectRequest = PutObjectRequest
      .builder()
      .bucket(bucketName)
      .contentLength(contentLenght)
      .contentType(contentType.getOrElse("plain/text"))
      .key(key)
      .build()
    val requestBody = AsyncRequestBody.fromPublisher(Task(content).toReactivePublisher)
    Task.fromFuture(
      s3Client.putObject(putObjectRequest, requestBody).asScala
    )
  }

  def getObject(bucketName: String, key: String)(implicit s3Client: S3AsyncClient): Task[ByteBuffer] = {
    val getObjectrequest = GetObjectRequest.builder().bucket(bucketName).key(key).build()
    Task.fromFuture(s3Client.getObject(getObjectrequest, new MonixS3AsyncResponseTransformer).asScala).flatten
  }

  /*def putObjectConsumer()(
    implicit s3Client: S3AsyncClient): Consumer[S3Object, Either[Throwable, PutObjectResult]] = {
    Consumer.create[S3Object, Either[Throwable, PutObjectResult]] { (_, _, callback) =>
      new Observer.Sync[S3Object] {
        private var putObjectResult: Either[Throwable, PutObjectResult] = _

        def onNext(s3Object: S3Object): Ack = {
          val S3Object(bucket, key, content) = s3Object
          putObjectResult = Try(s3Client.putObject(bucket, key, content)) match {
            case Success(putResult) => Right(putResult)
            case Failure(exception) => Left(exception)
          }
          monix.execution.Ack.Continue
        }

        def onComplete(): Unit = {
          callback.onSuccess(putObjectResult)
        }

        def onError(ex: Throwable): Unit = {
          callback.onError(ex)
        }
      }
    }
  }*/

}
