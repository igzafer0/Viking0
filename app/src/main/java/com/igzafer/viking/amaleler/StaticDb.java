package com.igzafer.viking.amaleler;

import com.igzafer.viking.Model.BlogModels.BlogModel;

import java.util.ArrayList;
import java.util.List;

public class StaticDb {
public static List<BlogModel> blogModel;
public static boolean homeFirst = true;
public static int kaydirma = 0;
public static void loadBlog(List<BlogModel> bm){
    blogModel= new ArrayList<>();
    blogModel= bm;
    homeFirst = false;
}
public static void setScrollPosition(int y){
    kaydirma=y;
}




}
