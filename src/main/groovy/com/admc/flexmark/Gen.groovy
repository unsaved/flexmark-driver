package com.admc.flexmark

import com.vladsch.flexmark.samples.BasicSample
import groovy.text.SimpleTemplateEngine

class Gen {
    final static String BP_PREFIX = 'boilerplates/'
    final static String DEFAULT_BP_BASE = 'basic'
    final static String BP_TOP_SUFFIX = '-top.html'
    final static String BP_BOTTOM_SUFFIX = '-bottom.html'

    private def boilerplatePre  // Class it properly when fucking API is online!
    private String boilerplatePost

    static private String sampleMd =
'''[TOC]
## Header lvl 2
Testing line breaks with line one
and line two with a http://cnn.com URL link,
and line three

A second paragraph with line onw
and line two

An here is a para  
With two explicit  
line breaks.  These suck since can't see them in the MD file.


```css
body {
    margin: 0;
    padding: 0;
    /* A CSS comment is here
    extending down to there */
}
```

### A table
Dimensions | Megapixels
---|---
1,920 x 1,080 | 2.1MP
3,264 x 2,448 | 8MP
4,288 x 3,216 | 14MP

'''

    private Map<String,String> substrMap = [
        headInsertion: '',
    ]

    void setTitle(String inTitle) {
        substrMap.title = inTitle
        //substrMap.headerInsertion = "<header>\n  <h1>$inTitle</h1>\n</header>\n"
        substrMap.headerInsertion = "<h1>$inTitle</h1>\n"
    }

    static private String SYNTAX_MSG = 
      $/flexmark-driver file/path.md 'The Title'/$

    static void main(String[] sa) {
        if (sa.length != 2) {
            System.err.println SYNTAX_MSG
            System.exit 2
        }
        File f = new File(sa[0])
        if (!f.isFile()) {
            System.err.println "No such file: ${sa[0]}"
            System.exit 2
        }
        //println new Gen(sa[0], 'bootstrap').html(f.text)
        println new Gen(sa[0]).html(f.text)
        /*
        Gen gen = new Gen('Fancy Titlage')
        println gen.cf(sampleMd)
        */
    }

    /**
     * Compares the provided ParserEmulationProfiles
     */
    String cf(String mdText) {
        if (!substrMap.title)
            throw new Error('Have not called setTitle for this Gen object')
        (boilerplatePre.make(substrMap).toString()
          + '\n<section>\n<h1>Default? Common</h1>'
          + BasicSample.commonMark(mdText)
          + '\n</section>\n<section>\n<h1>Explicit Common</h1>'
          + BasicSample.explicitCommon(mdText)
          + '\n</section>\n<section>\n<h1>Kramdown</h1>'
          + BasicSample.kramdown(mdText)
          + '\n</section>\n<section>\n<h1>Multi</h1>'
          + BasicSample.multiMarkdown(mdText)
          + '\n</section>\n<section>\n<h1>MD</h1>'
          + BasicSample.markdown(mdText)
          + '\n</section>\n<section>\n<h1>Pegdown</h1>'
          + BasicSample.pegdown(mdText)
          + '\n</section>\n<section>\n<h1>GFM</h1>'
          + BasicSample.gfm(mdText)
          + '\n</section>\n' + boilerplatePost)
    }

    String html(String mdText) {
        if (!substrMap.title)
            throw new Error('Have not called setTitle for this Gen object')
        (boilerplatePre.make(substrMap).toString()
          + BasicSample.explicitCommon(mdText) + '\n' + boilerplatePost)
    }

    Gen(String inTitle, String bpBase=DEFAULT_BP_BASE) throws IOException {
        InputStream iStream

        iStream = Thread.currentThread().
          getContextClassLoader().getResourceAsStream(
          BP_PREFIX + bpBase + BP_TOP_SUFFIX)
        if (!iStream)
            throw new IOException(
              "Boilerplate inaccessible: $DEFAULT_BPTOP_RES_PATH ")
        boilerplatePre = new SimpleTemplateEngine().
          createTemplate(iStream.text)
        iStream = Thread.currentThread().
          getContextClassLoader().getResourceAsStream(
          BP_PREFIX + bpBase + BP_BOTTOM_SUFFIX)
        if (!iStream)
            throw new IOException(
              "Boilerplate inaccessible: $DEFAULT_BPBOTTOM_RES_PATH ")
        boilerplatePost = iStream.text
        setTitle(inTitle)
    }
}
