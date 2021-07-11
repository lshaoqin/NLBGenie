package com.sq.nlbgeniev2

import android.content.Context
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley


const val postUrl = "https://openweb.nlb.gov.sg/ows/CatalogueService.svc"


class XMLClient {
    interface VolleyCallback {
        fun onSuccess(result: String?)
    }
    fun keywordSearch(keyword: String, author: String, library: String, searches: String, context: Context,callback: VolleyCallback){
        var output = ""
        val postUrl = "https://openweb.nlb.gov.sg/ows/CatalogueService.svc"
        val requestQueue: RequestQueue = Volley.newRequestQueue(context)
        var postData = ""
        if(author.isBlank()) {
            postData =
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:cat=\"http://www.nlb.gov.sg/ws/CatalogueService\">\n" +
                        "   <soapenv:Header/>\n" +
                        "   <soapenv:Body>\n" +
                        "      <cat:SearchRequest>\n" +
                        "         <!--Optional:-->\n" +
                        "         <cat:APIKey>APIKey</cat:APIKey>\n" +
                        "         <!--Optional:-->\n" +
                        "         <cat:SearchItems>\n" +
                        "            <!--Zero or more repetitions:-->\n" +
                        "            <cat:SearchItem>\n" +
                        "               <!--Optional:-->\n" +
                        "               <cat:SearchField>Title</cat:SearchField>\n" +
                        "               <!--Optional:-->\n" +
                        "               <cat:SearchTerms>$keyword</cat:SearchTerms>\n" +
                        "            </cat:SearchItem>\n" +
                        "            <cat:SearchItem>\n" +
                        "               <!--Optional:-->\n" +
                        "               <cat:SearchField>BranchID</cat:SearchField>\n" +
                        "               <!--Optional:-->\n" +
                        "               <cat:SearchTerms>$library</cat:SearchTerms>\n" +
                        "            </cat:SearchItem>" +

                        "         </cat:SearchItems>\n" +
                        "         <!--Optional:-->\n" +
                        "         <cat:Modifiers>\n" +
                        "            <!--Optional:-->\n" +
                        "            <cat:SortSchema></cat:SortSchema>\n" +
                        "            <cat:StartRecordPosition>0</cat:StartRecordPosition>\n" +
                        "            <cat:MaximumRecords>$searches</cat:MaximumRecords>\n" +
                        "            <!--Optional:-->\n" +
                        "            <cat:SetId></cat:SetId>\n" +
                        "         </cat:Modifiers>\n" +
                        "      </cat:SearchRequest>\n" +
                        "   </soapenv:Body>\n" +
                        "</soapenv:Envelope>"
        }
        else if(keyword.isBlank()) {
            postData =
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:cat=\"http://www.nlb.gov.sg/ws/CatalogueService\">\n" +
                        "   <soapenv:Header/>\n" +
                        "   <soapenv:Body>\n" +
                        "      <cat:SearchRequest>\n" +
                        "         <!--Optional:-->\n" +
                        "         <cat:APIKey>APIKey</cat:APIKey>\n" +
                        "         <!--Optional:-->\n" +
                        "         <cat:SearchItems>\n" +
                        "            <!--Zero or more repetitions:-->\n" +
                        "            <cat:SearchItem>\n" +
                        "               <!--Optional:-->\n" +
                        "               <cat:SearchField>Author</cat:SearchField>\n" +
                        "               <!--Optional:-->\n" +
                        "               <cat:SearchTerms>$author</cat:SearchTerms>\n" +
                        "            </cat:SearchItem>\n" +
                        "            <cat:SearchItem>\n" +
                        "               <!--Optional:-->\n" +
                        "               <cat:SearchField>BranchID</cat:SearchField>\n" +
                        "               <!--Optional:-->\n" +
                        "               <cat:SearchTerms>$library</cat:SearchTerms>\n" +
                        "            </cat:SearchItem>" +

                        "         </cat:SearchItems>\n" +
                        "         <!--Optional:-->\n" +
                        "         <cat:Modifiers>\n" +
                        "            <!--Optional:-->\n" +
                        "            <cat:SortSchema></cat:SortSchema>\n" +
                        "            <cat:StartRecordPosition>0</cat:StartRecordPosition>\n" +
                        "            <cat:MaximumRecords>$searches</cat:MaximumRecords>\n" +
                        "            <!--Optional:-->\n" +
                        "            <cat:SetId></cat:SetId>\n" +
                        "         </cat:Modifiers>\n" +
                        "      </cat:SearchRequest>\n" +
                        "   </soapenv:Body>\n" +
                        "</soapenv:Envelope>"
        }
        else {
            postData =
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:cat=\"http://www.nlb.gov.sg/ws/CatalogueService\">\n" +
                        "   <soapenv:Header/>\n" +
                        "   <soapenv:Body>\n" +
                        "      <cat:SearchRequest>\n" +
                        "         <!--Optional:-->\n" +
                        "         <cat:APIKey>APIKey</cat:APIKey>\n" +
                        "         <!--Optional:-->\n" +
                        "         <cat:SearchItems>\n" +
                        "            <!--Zero or more repetitions:-->\n" +
                        "            <cat:SearchItem>\n" +
                        "               <!--Optional:-->\n" +
                        "               <cat:SearchField>Title</cat:SearchField>\n" +
                        "               <!--Optional:-->\n" +
                        "               <cat:SearchTerms>$keyword</cat:SearchTerms>\n" +
                        "            </cat:SearchItem>\n" +
                        "            <cat:SearchItem>\n" +
                        "               <!--Optional:-->\n" +
                        "               <cat:SearchField>Author</cat:SearchField>\n" +
                        "               <!--Optional:-->\n" +
                        "               <cat:SearchTerms>$author</cat:SearchTerms>\n" +
                        "            </cat:SearchItem>\n" +
                        "            <cat:SearchItem>\n" +
                        "               <!--Optional:-->\n" +
                        "               <cat:SearchField>BranchID</cat:SearchField>\n" +
                        "               <!--Optional:-->\n" +
                        "               <cat:SearchTerms>$library</cat:SearchTerms>\n" +
                        "            </cat:SearchItem>" +

                        "         </cat:SearchItems>\n" +
                        "         <!--Optional:-->\n" +
                        "         <cat:Modifiers>\n" +
                        "            <!--Optional:-->\n" +
                        "            <cat:SortSchema></cat:SortSchema>\n" +
                        "            <cat:StartRecordPosition>0</cat:StartRecordPosition>\n" +
                        "            <cat:MaximumRecords>$searches</cat:MaximumRecords>\n" +
                        "            <!--Optional:-->\n" +
                        "            <cat:SetId></cat:SetId>\n" +
                        "         </cat:Modifiers>\n" +
                        "      </cat:SearchRequest>\n" +
                        "   </soapenv:Body>\n" +
                        "</soapenv:Envelope>"
        }
        val strReq: StringRequest = object : StringRequest(
                Method.POST,
                postUrl, Response.Listener<String?> { response ->
            if (response != null) {
                callback.onSuccess(response)

            }
        }, Response.ErrorListener { error ->
            if (error.networkResponse != null) {
                val responseBody = String(error.networkResponse.data, charset("utf-8"))
                output = "ERROR$responseBody"
            }

        }) {

            override fun getBodyContentType(): String {
                return "text/xml;charset=UTF-8"
            }

            @Throws(AuthFailureError::class)
            override fun getBody(): ByteArray {
                return postData.toByteArray(charset("UTF-8"))
            }

            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String>{
                val map = HashMap<String, String>()
                map.put("Content-Length", postData.length.toString())
                map.put("SOAPAction", "http://www.nlb.gov.sg/ws/CatalogueService/ICatalogueService/Search");
                return map;
            }


        }
        strReq.retryPolicy = DefaultRetryPolicy(
            3000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        requestQueue.add(strReq)
    }





    fun GetAvailabilityInfo(BID: String, context: Context,callback: VolleyCallback) {
        var output = ""
        val requestQueue: RequestQueue = Volley.newRequestQueue(context)
        val postData =
            "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:cat=\"http://www.nlb.gov.sg/ws/CatalogueService\">\n" +
                    "       <soapenv:Header/>\n" +
                    "       <soapenv:Body>\n" +
                    "          <cat:GetAvailabilityInfoRequest>\n" +
                    "             <!--Optional:-->\n" +
                    "             <cat:APIKey>APIKey</cat:APIKey>\n" +
                    "             <!--Optional:-->\n" +
                    "             <cat:BID>$BID</cat:BID>\n" +
                    "             <!--Optional:-->\n" +
                    "             <cat:ISBN></cat:ISBN>\n" +
                    "             <!--Optional:-->\n" +
                    "             <cat:Modifiers>\n" +
                    "            <cat:StartRecordPosition>0</cat:StartRecordPosition>\n" +
                    "            <cat:MaximumRecords>1000</cat:MaximumRecords>\n" +
                    "                <cat:SetId></cat:SetId>\n" +
                    "             </cat:Modifiers>\n" +
                    "          </cat:GetAvailabilityInfoRequest>\n" +
                    "       </soapenv:Body>\n" +
                    "    </soapenv:Envelope>"
        val strReq: StringRequest = object : StringRequest(Method.POST,
            postUrl, Response.Listener<String?> { response ->
                if (response != null) {
                    callback.onSuccess(response)
                }
            }, Response.ErrorListener {

            }) {
            override fun getBodyContentType(): String {
                return "text/xml;charset=UTF-8"
            }

            @Throws(AuthFailureError::class)
            override fun getBody(): ByteArray {
                return postData.toByteArray(charset("UTF-8"))
            }


            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val map = HashMap<String, String>()
                map.put("Content-Length", postData.length.toString())
                map.put(
                    "SOAPAction",
                    "http://www.nlb.gov.sg/ws/CatalogueService/ICatalogueService/GetAvailabilityInfo"
                );
                return map;
            }
        }
        strReq.retryPolicy = DefaultRetryPolicy(
            3000,
           5,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        requestQueue.add(strReq)
    }
}

