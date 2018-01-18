package toliner.kotatu

import com.beust.klaxon.Klaxon
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.content.IncomingContent
import io.ktor.content.TextContent
import io.ktor.content.readText
import io.ktor.features.ContentConverter
import io.ktor.features.ContentNegotiation
import io.ktor.features.suitableCharset
import io.ktor.http.ContentType
import io.ktor.http.withCharset
import io.ktor.pipeline.PipelineContext
import io.ktor.request.ApplicationReceiveRequest

class KlaxonConverter(private val klaxon: Klaxon = Klaxon()) : ContentConverter {

    override suspend fun convertForSend(context: PipelineContext<Any, ApplicationCall>, contentType: ContentType, value: Any): Any? {
        return TextContent(klaxon.toJsonString(value), contentType.withCharset(context.call.suitableCharset()))
    }

    override suspend fun convertForReceive(context: PipelineContext<ApplicationReceiveRequest, ApplicationCall>): Any? {
        val request = context.subject
        return klaxon.fromJsonObject(klaxon.parseJsonObject((request.value as? IncomingContent ?: return null).readText().reader()), request.type.javaObjectType, request.type)
    }

}

fun ContentNegotiation.Configuration.klaxon(block: Klaxon.() -> Unit) {
    val converter = KlaxonConverter(Klaxon().apply(block))
    register(ContentType.Application.Json, converter)
}

fun ContentNegotiation.Configuration.klaxon() {
    register(ContentType.Application.Json, KlaxonConverter())
}
