package org.vcs;

import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;

public class EntryPoint implements BurpExtension {
    private String extName = "Name of the extensions";
    public static MontoyaApi api;
    @Override
    public void initialize(MontoyaApi api) {
        this.api = api;

        api.extension().setName(this.extName);

        api.http().registerHttpHandler(new CustomHttpHandler());
    }
}
