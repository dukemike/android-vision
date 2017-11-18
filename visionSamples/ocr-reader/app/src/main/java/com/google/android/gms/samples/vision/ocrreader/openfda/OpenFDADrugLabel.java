package com.google.android.gms.samples.vision.ocrreader.openfda;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OpenFDADrugLabel {
    private List<String> description;
    @SerializedName("adverse_reactions")
    private List<String> adverseReactions;
    @SerializedName("information_for_patients")
    private List<String> informationForPatients;
    @SerializedName("dosage_and_administration")
    private List<String> dosageAndAdministration;
    @SerializedName("spl_medguide")
    private List<String> medicationGuide;
    @SerializedName("do_not_use")
    private List<String> doNotUseIf;
    @SerializedName("instructions_for_use")
    private List<String> instructionsForUse;
    private List<String> overdosage;
    private List<String> purpose;
    @SerializedName("openfda")
    private OpenFDABlock mOpenFDA;

    private boolean warningForAlcohol;
    private boolean warningForNausea;
    private boolean warningForDizziness;
    private boolean warningForDrowsines;
    private boolean warningAgainstChewing;
    private boolean warningForLethalOverdose;

    private @Nullable
    String asString(@Nullable Iterable iterable) {
        return iterable == null ? null : TextUtils.join(" ", iterable);
    }

    public void cleanValues() {
        if (mOpenFDA == null) {mOpenFDA = new OpenFDABlock();}

        if (description != null) {
            String s = getDescription();
            s = s.replaceAll("^(\\d*\\.?\\s?)?DESCRIPTION ", "");
            description = Arrays.asList(s);
        }
        if (purpose != null) {
            String s = getPurpose().replaceAll("^Purpose ", "");
            purpose = Arrays.asList(s);
        }
        if (adverseReactions != null) {
            String s = getAdverseReactions().replaceAll("^(\\d*\\.?\\s?)?ADVERSE REACTIONS ", "");
            adverseReactions = Arrays.asList(s);
        }
        if (informationForPatients != null) {
            String s = getInformationForPatients().replaceAll("^(\\d*\\.?\\s?)?PATIENT COUNSELING INFORMATION ", "");
            informationForPatients = Arrays.asList(s);
        }

        if (!warningForAlcohol) {
            warningForAlcohol = containsWarningForAlcohol(getAdverseReactions());
        }
        if (!warningForAlcohol) {
            warningForAlcohol = containsWarningForAlcohol(getInformationForPatients());
        }
// ignore "alcohol" in description field
//        if (!warningForAlcohol) {
//            warningForAlcohol = containsWarningForAlcohol(getDescription());
//        }

        if (!warningForNausea) {
            warningForNausea = containsWarningForNausea(getAdverseReactions());
        }
        if (!warningForNausea) {
            warningForNausea = containsWarningForNausea(getInformationForPatients());
        }
        if (!warningForNausea) {
            warningForNausea = containsWarningForNausea(getDescription());
        }

        if (!warningForDizziness) {
            warningForDizziness = containsWarningForDizziness(getAdverseReactions());
        }
        if (!warningForDizziness) {
            warningForDizziness = containsWarningForDizziness(getInformationForPatients());
        }
        if (!warningForDizziness) {
            warningForDizziness = containsWarningForDizziness(getDescription());
        }

        if (!warningForDrowsines) {
            warningForDrowsines = containsWarningForDrowsiness(getAdverseReactions());
        }
        if (!warningForDrowsines) {
            warningForDrowsines = containsWarningForDrowsiness(getInformationForPatients());
        }
        if (!warningForDrowsines) {
            warningForDrowsines = containsWarningForDrowsiness(getDescription());
        }

        if (!warningAgainstChewing) {
            warningAgainstChewing = containsWarningAgainstChewing(getDosageAndAdministration());
        }
        if (!warningAgainstChewing) {
            warningAgainstChewing = containsWarningAgainstChewing(getInformationForPatients());
        }
        if (!warningAgainstChewing) {
            warningAgainstChewing = containsWarningAgainstChewing(getMedicationGuide());
        }
        if (!warningAgainstChewing) {
            warningAgainstChewing = containsWarningAgainstChewing(getInstructionsForUse());
        }

        if (!warningForLethalOverdose) {
            warningForLethalOverdose = containsWarningForLeathalOverdose(getOverdosage());
        }
    }

    private boolean containsWarningForAlcohol(String string) {
        if (string == null) return false;
        return string.toLowerCase().indexOf("alcohol") > -1;
    }

    private boolean containsWarningForNausea(String string) {
        if (string == null) return false;
        return string.toLowerCase().indexOf("nausea") > -1;
    }

    private boolean containsWarningForDizziness(String string) {
        if (string == null) return false;
        return string.toLowerCase().indexOf("dizziness") > -1;
    }

    private boolean containsWarningForDrowsiness(String string) {
        if (string == null) return false;
        return string.toLowerCase().indexOf("drowsiness") > -1;
    }

    private boolean containsWarningForLeathalOverdose(String string) {
        if (string == null) return false;
        return string.toLowerCase().indexOf("lethal") > -1;
    }

    private boolean containsWarningAgainstChewing(String string) {
        if (string == null) return false;
        boolean contains = string.toLowerCase().indexOf("should not be crushed or chewed") > -1;
        if (!contains) {
             contains = string.toLowerCase().indexOf("do not crush or chew") > -1;
        }
        return contains;
    }


    public String getDescription() {
        return asString(description);
    }

    public String getAdverseReactions() {
        return asString(adverseReactions);
    }

    public String getDoNotUseIf() {
        return asString(doNotUseIf);
    }

    public String getPurpose() {
        return asString(purpose);
    }

    public OpenFDABlock getOpenFDA() {
        return mOpenFDA;
    }

    public String getInformationForPatients() {
        return asString(informationForPatients);
    }

    public String getDosageAndAdministration() {
        return asString(dosageAndAdministration);
    }

    public String getMedicationGuide() {
        return asString(medicationGuide);
    }

    public String getInstructionsForUse() {
        return asString(instructionsForUse);
    }

    public String getOverdosage() {
        return asString(overdosage);
    }

    public boolean isWarningForAlcohol() {
        return warningForAlcohol;
    }

    public boolean isWarningForNausea() {
        return warningForNausea;
    }

    public boolean isWarningForDizziness() {
        return warningForDizziness;
    }

    public boolean isWarningForDrowsines() {
        return warningForDrowsines;
    }

    public boolean isWarningAgainstChewing() {
        return warningAgainstChewing;
    }

    public boolean isWarningForLethalOverdose() {
        return warningForLethalOverdose;
    }
}
