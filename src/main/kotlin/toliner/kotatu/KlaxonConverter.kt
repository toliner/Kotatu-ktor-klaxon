package toliner.kotatu

import com.beust.klaxon.Klaxon
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.content.IncomingContent
import io.ktor.content.TextContent
import io.ktor.content.readText
import io.ktor.features.ContentConverter
import io.ktor.features.suitableCharset
import io.ktor.http.ContentType
import io.ktor.http.withCharset
import io.ktor.pipeline.PipelineContext
import io.ktor.request.ApplicationReceiveRequest

class KlaxonConverter : ContentConverter {
    private val klaxon = Klaxon()

    suspend override fun convertForSend(context: PipelineContext<Any, ApplicationCall>, contentType: ContentType, value: Any): Any? {
        return TextContent(klaxon.toJsonString(value), contentType.withCharset(context.call.suitableCharset()))
    }

    suspend override fun convertForReceive(context: PipelineContext<ApplicationReceiveRequest, ApplicationCall>): Any? {
        val request = context.subject
        return klaxon.fromJsonObject(klaxon.parseJsonObject((request.value as? IncomingContent ?: return null).readText().reader()), request.type.javaObjectType, request.type)
    }

}
