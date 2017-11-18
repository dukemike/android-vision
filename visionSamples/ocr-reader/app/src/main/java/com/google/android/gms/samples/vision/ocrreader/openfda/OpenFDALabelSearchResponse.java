package com.google.android.gms.samples.vision.ocrreader.openfda;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OpenFDALabelSearchResponse {
    @SerializedName("results")
    private List<OpenFDADrugLabel> mDrugLabels;

    public List<OpenFDADrugLabel> getDrugLabels() {
        return mDrugLabels;
    }
}
