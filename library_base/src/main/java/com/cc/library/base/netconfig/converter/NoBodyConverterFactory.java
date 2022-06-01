package com.cc.library.base.netconfig.converter;

import com.cc.library.base.entity.NoBodyResponse;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class NoBodyConverterFactory extends Converter.Factory{


    public static NoBodyConverterFactory create(){
        return new NoBodyConverterFactory();
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        //判断当前的类型是否是我们需要处理的类型
        if (NoBodyResponse.class.equals(type)) {
            //是则创建一个Converter返回转换数据
            return (Converter<ResponseBody, NoBodyResponse>) value -> {
                //这里直接返回null是因为我们不需要使用到响应体,本来也没有响应体.
                //返回的对象会存到response.body()里.
                return null;
            };
        }
        return null;
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return super.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit);
    }


    @Override
    public Converter<?, String> stringConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return super.stringConverter(type, annotations, retrofit);
    }
}
