import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.http.*

val client = HttpClient(CIO) {
    install(Logging) {
        logger = Logger.SIMPLE
        level = LogLevel.INFO

    }
    install(JsonFeature) {
        serializer = KotlinxSerializer()
    }

    defaultRequest{
//        parameter("Accept", "application/json")
//        parameter("Content-Type", "application/json; charset=\"UTF-8\"")
        header("Authorization", "Bearer "+PrivateConst.API_KEY)
        url {
            protocol = URLProtocol.HTTPS
            host = "api.robinhood.com"
//            encodedPath = "$basePath$encodedPath"
        }
        Parameters.build { urlEncodingOption = UrlEncodingOption.DEFAULT }
    }
}

