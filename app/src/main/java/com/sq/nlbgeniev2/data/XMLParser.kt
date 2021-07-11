package com.sq.nlbgeniev2.data

import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream

class BIDTitle(val BID: String, val Title: String)

class XMLParser {
    fun searchParse(inputStream: InputStream): List<BIDTitle> {
        val BIDs = mutableListOf<BIDTitle>()
        var BID = ""
        var title = ""
        val factory = XmlPullParserFactory.newInstance()
        factory.isNamespaceAware = true
        val parser = factory.newPullParser()
        parser.setInput(inputStream, null)
        var eventType: Int = parser.eventType
        //Log.d("start2", inputStream.bufferedReader().use { it.readText() })
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {
                if (parser.name == "BID") {
                    parser.next()
                    BID = parser.text
                }
                if (parser.name == "TitleName"){
                    parser.next()
                    title = parser.text
                }
            }
            else if(eventType == XmlPullParser.END_TAG){
                if(parser.name == "Title"){
                    BIDs += BIDTitle(BID,title)
                }
            }
            eventType = parser.next()
        }

        return BIDs
    }


    fun availParse(inputStream: InputStream, branchID: String): String? {
        var branch = ""
        var status = ""
        var callno = ""
        val factory = XmlPullParserFactory.newInstance()
        factory.isNamespaceAware = true
        val parser = factory.newPullParser()
        parser.setInput(inputStream, null)
        var eventType = parser.eventType
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {
                if (parser.name == "BranchID") {
                    parser.next()
                    if (parser.text != null) {
                        branch = parser.text
                    }
                }
                if (parser.name == "StatusDesc") {
                    parser.next()
                    if (parser.text != null) {
                        status = parser.text
                    }
                }
                if (parser.name == "CallNumber") {
                    parser.next()
                    if (parser.text != null) {
                        callno = parser.text
                    }
                }
            }
            if(eventType == XmlPullParser.END_TAG && parser.name == "Item"){
                if (branch == branchID && status == "Not on Loan") {
                    return callno
            }
        }
            eventType = parser.next()
        }
        return null
    }
}



