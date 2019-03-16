package com.example.demo.mapper;

import com.example.demo.Product;
import org.apache.ibatis.annotations.*;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ProductMapper {


    Product select(
            @Param("id")
                    long id);

    void update(Product product);

    List<Product> findAll();

    long getPrice(@Param("id") long id);





    /*@Select("select * from Product where id = #{id}")
    @Results(value={@Result(column = "id", property = "id", javaType = long.class),
            @Result(property = "name", column = "name", javaType = String.class),
            @Result(column = "price", property = "price", javaType = long.class)
            })
    Product select(@Param("id") long id);

    @Update("UPDATE Product SET name=#{name},price=#{price} WHERE id =#{id}")
    void update(Product product);*/
}