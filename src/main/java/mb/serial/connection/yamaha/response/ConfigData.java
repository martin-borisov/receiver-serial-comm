package mb.serial.connection.yamaha.response;

public class ConfigData {
    
    private String powerState, inputSource, inputMode, 
        muteState, masterVolume, tunerPage, tunerPreset;
    
    public ConfigData(String powerState) {
        this.powerState = powerState;
    }

    public ConfigData(String powerState, String inputSource, String inputMode, String muteState, String masterVolume,
            String tunerPage, String tunerPreset) {
        this.powerState = powerState;
        this.inputSource = inputSource;
        this.inputMode = inputMode;
        this.muteState = muteState;
        this.masterVolume = masterVolume;
        this.tunerPage = tunerPage;
        this.tunerPreset = tunerPreset;
    }

    public String getPowerState() {
        return powerState;
    }

    public void setPowerState(String powerState) {
        this.powerState = powerState;
    }

    public String getInputSource() {
        return inputSource;
    }

    public void setInputSource(String inputSource) {
        this.inputSource = inputSource;
    }

    public String getInputMode() {
        return inputMode;
    }

    public void setInputMode(String inputMode) {
        this.inputMode = inputMode;
    }

    public String getMuteState() {
        return muteState;
    }

    public void setMuteState(String muteState) {
        this.muteState = muteState;
    }

    public String getMasterVolume() {
        return masterVolume;
    }

    public void setMasterVolume(String masterVolume) {
        this.masterVolume = masterVolume;
    }

    public String getTunerPage() {
        return tunerPage;
    }

    public void setTunerPage(String tunerPage) {
        this.tunerPage = tunerPage;
    }

    public String getTunerPreset() {
        return tunerPreset;
    }

    public void setTunerPreset(String tunerPreset) {
        this.tunerPreset = tunerPreset;
    }

    @Override
    public String toString() {
        return "ConfigData [powerState=" + powerState + ", inputSource=" + inputSource + ", inputMode=" + inputMode
                + ", muteState=" + muteState + ", masterVolume=" + masterVolume + ", tunerPage=" + tunerPage
                + ", tunerPreset=" + tunerPreset + "]";
    }
}
