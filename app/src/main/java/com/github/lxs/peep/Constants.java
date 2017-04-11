package com.github.lxs.peep;

import com.github.lxs.peep.utils.SDCardUtils;

public class Constants {

    public static final boolean SHOW_DEBUG = true;
    public static String IMG_CACHE_PATH = SDCardUtils.getSDCardPath() + "/peep/imgCache";

    public static String HTTP_CACHE_PATH = SDCardUtils.getSDCardPath() + "/peep/httpCache";

    public static String DOWNLOAD_PATH = SDCardUtils.getSDCardPath() + "/peep/download";
}
