package com.skloda;

import com.skloda.spark.Car;
import com.skloda.spark.WordCountLocal;

/**
 * @Author: jiangkun
 * @Description:
 * @Date: Created in 2019-06-05 16:54
 */
public class TestJavaWithScala {

    public static void main(String[] args) {
        // 调用scala的class
        Car car = new Car(2008);
        car.drive(10000);
        System.out.println(car);

        // 调用scala的object
        WordCountLocal.main(args);
    }
}
