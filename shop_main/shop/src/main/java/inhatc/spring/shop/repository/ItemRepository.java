package inhatc.spring.shop.repository;

import inhatc.spring.shop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long>, QuerydslPredicateExecutor<Item> {

    //JPQL
    @Query("select i from Item i where i.itemDetail like %:itemDetail% order by i.price desc")
    List<Item> findByDetail(@Param("itemDetail") String itemDetail);

    @Query(value = "select * from Item i where i.item_Detail like %:itemDetail% order by i.price asc", nativeQuery = true)
    List<Item> findByDetailNative(@Param("itemDetail") String itemDetail);

    List<Item> findByItemNm(String itemNm);

    List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail);

    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);


    //과제 쿼리문으로
    List<Item> findByStockNumberGreaterThanEqualAndItemNmLikeOrderByPriceAsc(Integer stockNumber, String itemNm);
    //과제 JPQL로
    @Query("select i from Item i where i.stockNumber >= %:stockNumber% and i.itemNm like %:itemNm% order by i.price asc")
    List<Item> findByStockNumberItemNm(@Param("stockNumber") Integer stockNumber, @Param("itemNm") String itemNm);
    //과제 Native로
    @Query(value = "select * from Item i where i.stock_Number >= %:stockNumber% and i.item_Nm like %:itemNm% order by i.price asc",
            nativeQuery = true)
    List<Item> findByStockNumberAndItemNmNative(@Param("stockNumber") Integer stockNumber, @Param("itemNm") String itemNm);

}
