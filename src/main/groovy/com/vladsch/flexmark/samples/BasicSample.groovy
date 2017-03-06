package com.vladsch.flexmark.samples

import com.vladsch.flexmark.ast.Node
import com.vladsch.flexmark.ext.abbreviation.AbbreviationExtension
import com.vladsch.flexmark.ext.definition.DefinitionExtension
import com.vladsch.flexmark.ext.footnotes.FootnoteExtension
import com.vladsch.flexmark.ext.tables.TablesExtension
import com.vladsch.flexmark.ext.typographic.TypographicExtension
import com.vladsch.flexmark.ext.toc.TocExtension
import com.vladsch.flexmark.ext.autolink.AutolinkExtension
import com.vladsch.flexmark.html.HtmlRenderer
import com.vladsch.flexmark.parser.Parser
import com.vladsch.flexmark.parser.ParserEmulationProfile
import com.vladsch.flexmark.util.options.MutableDataSet

/**
 * Groovified from https://github.com/vsch/flexmark-java/blob/master/flexmark-java-samples/src/com/vladsch/flexmark/samples/BasicSample.java
 */
class BasicSample {
    static private List optList = [
        AbbreviationExtension.create(),
        DefinitionExtension.create(),
        FootnoteExtension.create(),
        TablesExtension.create(),
        TypographicExtension.create(),
        TocExtension.create(),
        AutolinkExtension.create()
    ]

    static String commonMark(String inS) {
        MutableDataSet options = new MutableDataSet()
        options.set Parser.EXTENSIONS, optList
        Parser parser = Parser.builder().build()
        Node document = parser.parse inS
        HtmlRenderer renderer = HtmlRenderer.builder(options).build()
        renderer.render document
    }

    static String kramdown(String inS) {
        /* For unknown reason, the sample config for only Kramdown
         * sets some extension options */
        MutableDataSet options = new MutableDataSet()
        options.setFrom ParserEmulationProfile.KRAMDOWN
        options.set Parser.EXTENSIONS, optList

        Parser parser = Parser.builder(options).build()
        HtmlRenderer renderer = HtmlRenderer.builder(options).build()

        Node document = parser.parse inS
        renderer.render document
    }

    static String multiMarkdown(String inS) {
        MutableDataSet options = new MutableDataSet()
        options.setFrom ParserEmulationProfile.MULTI_MARKDOWN
        options.set Parser.EXTENSIONS, optList

        Parser parser = Parser.builder(options).build()
        HtmlRenderer renderer = HtmlRenderer.builder(options).build()

        Node document = parser.parse inS
        renderer.render document
    }

    static String markdown(String inS) {
        MutableDataSet options = new MutableDataSet()
        options.setFrom ParserEmulationProfile.MARKDOWN
        options.set Parser.EXTENSIONS, optList

        Parser parser = Parser.builder(options).build()
        HtmlRenderer renderer = HtmlRenderer.builder(options).build()

        Node document = parser.parse inS
        renderer.render document
    }

    // Blaine adds these other variants

    static String explicitCommon(String inS) {
        MutableDataSet options = new MutableDataSet()
        options.setFrom ParserEmulationProfile.COMMONMARK
        options.set Parser.EXTENSIONS, optList

        Parser parser = Parser.builder(options).build()
        HtmlRenderer renderer = HtmlRenderer.builder(options).build()

        Node document = parser.parse inS
        renderer.render document
    }

    static String pegdown(String inS) {
        MutableDataSet options = new MutableDataSet()
        options.setFrom ParserEmulationProfile.PEGDOWN
        options.set Parser.EXTENSIONS, optList

        Parser parser = Parser.builder(options).build()
        HtmlRenderer renderer = HtmlRenderer.builder(options).build()

        Node document = parser.parse inS
        renderer.render document
    }

    static String gfm(String inS) {
        MutableDataSet options = new MutableDataSet()
        options.setFrom ParserEmulationProfile.GITHUB_DOC
        options.set Parser.EXTENSIONS, optList

        Parser parser = Parser.builder(options).build()
        HtmlRenderer renderer = HtmlRenderer.builder(options).build()

        Node document = parser.parse inS
        renderer.render document
    }
}
