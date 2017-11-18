package com.google.android.gms.samples.vision.ocrreader.openfda;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OpenFDABlock {
    @SerializedName("generic_name")
    private List<String> genericName;
    @SerializedName("brand_name")
    private List<String> brandName;

    public String getGenericName() {
        return genericName == null ? null : TextUtils.join(" ", genericName);
    }

    public String getBrandName() {
        return brandName == null ? null : TextUtils.join(" ", brandName);
    }
}
