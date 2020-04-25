/*
 * Copyright (c) 2014-2020 by The Monix Project Developers.
 * See the project homepage at: https://monix.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package monix.connect.dynamodb

import java.net.URI

import DynamoAppConfig.DynamoDbConfig
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
import software.amazon.awssdk.regions.Region

object DynamoDbClient {
  def apply(): DynamoDbAsyncClient = {
    val config: DynamoDbConfig = DynamoAppConfig.load()
    DynamoDbAsyncClient
      .builder()
      .credentialsProvider(config.awsCredProvider)
      .endpointOverride(new URI(config.endPoint))
      .region(Region.AWS_GLOBAL)
      .build()
  }
}
