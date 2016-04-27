package com.heaven.common.http;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.google.gson.JsonSyntaxException;
import com.heaven.common.R;

/**
 * Created by neusoft on 2016/4/27.
 */
public class VelloyHttpError {
    public static final int TIMEOUTE_ERROR = 1;
    public static final int NETWORKE_ERROR = 2;
    public static final int NO_CONNECTION_ERROR = 3;
    public static final int SERVER_ERROR = 4;
    public static final int AUTHFAILURE_ERROR = 5;
    public static final int SESSION_ERROR = 6;
    public static final int CHECK_CODE_ERROR = 7;
    public static final int PROTOCOL_ANALYZE_ERROR = 8;
    public static final int OTHER_ERROR = 9;

    /**
     * classify the error type
     * @param error error message
     * @return error type
     */
    public static ErrorMessage dealWithErrorType(Object error,Context context) {
        ErrorMessage errorMessage = new ErrorMessage();
        if (error != null) {
            if (error instanceof ServerError) {
                errorMessage.mErrorType = SERVER_ERROR;
                errorMessage.mErrorDetail = handleServerError((VolleyError)error, context);
            } else if (error instanceof TimeoutError) {
                errorMessage.mErrorType = TIMEOUTE_ERROR;
                errorMessage.mErrorDetail = context.getResources().getString(R.string.timeoute_error);
            } else if (error instanceof NetworkError) {
                errorMessage.mErrorType = NETWORKE_ERROR;
                errorMessage.mErrorDetail = context.getResources().getString(R.string.networke_error);
            } else if (error instanceof NoConnectionError) {
                errorMessage.mErrorType = NO_CONNECTION_ERROR;
                errorMessage.mErrorDetail = context.getResources().getString(R.string.no_connection_error);
            } else if (error instanceof AuthFailureError) {
                errorMessage.mErrorType = AUTHFAILURE_ERROR;
                errorMessage.mErrorDetail = context.getResources().getString(R.string.authfailure_error);
            } else if (error instanceof VolleyError) {
                Throwable exception = ((VolleyError) error).getCause();
                if (exception != null && exception instanceof JsonSyntaxException) {
                    errorMessage.mErrorType = PROTOCOL_ANALYZE_ERROR;
                    errorMessage.mErrorDetail = context.getResources().getString(R.string.protocol_analyze_error);
                }
            } else if (error instanceof ErrorMessage) {
                errorMessage = (ErrorMessage)error;
            }
        }
        return errorMessage;
    }

    /**
     * Handles the server error, tries to determine whether to show a stock message or to
     * show a message retrieved from the server.
     *
     * @param error error Object
     * @param context context
     * @return error message
     */
    private static String handleServerError(VolleyError error, Context context) {
        NetworkResponse response = error.networkResponse;
        String serverErrorMessage = context.getResources().getString(R.string.server_error);
        if (response != null) {
            switch (response.statusCode) {
                case 404:
                    serverErrorMessage = context.getResources().getString(R.string.resource_not_find_error);
                    break;
                case 422:
                    serverErrorMessage = context.getResources().getString(R.string.service_conn_num_out_error);
                    break;
                case 401:
                    serverErrorMessage = context.getResources().getString(R.string.authorization_error);
                    break;
//                    try {
//                        // server might return error like this { "error": "Some error occured" }
//                        // Use "Gson" to parse the result
//                        HashMap<String, String> result = new Gson().fromJson(new String(response.data),
//                                new TypeToken<Map<String, String>>() {
//                                }.getType());
//                        if (result != null && result.containsKey("error")) {
//                            return result.get("error");
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    // invalid request
//                    return error.getMessage();
                default:
                    serverErrorMessage = context.getResources().getString(R.string.server_error);;
            }
        }
        return serverErrorMessage;
    }
}
