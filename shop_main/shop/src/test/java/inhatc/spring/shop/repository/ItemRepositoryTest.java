package inhatc.spring.shop.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import inhatc.spring.shop.constant.ItemSellStatus;
import inhatc.spring.shop.entity.Item;
import inhatc.spring.shop.entity.QItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

import static inhatc.spring.shop.entity.QItem.item;


@SpringBootTest
//@Transactional
class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private EntityManager em;

    public void createItemList(){
        for (int i = 1; i <= 100; i++) {
            Item item = Item.builder()
                    .itemNm("테스트 상품" + i)
                    .price(10000 + i)
                    .stockNumber(100 + i)
                    .itemDetail("테스트 상품에 대한 상세설명" + i)
                    .itemSellStatus(ItemSellStatus.SELL)
                    .regTime(LocalDateTime.now())
                    .updateTime(LocalDateTime.now())
                    .build();
            itemRepository.save(item);
        }
    }
    public void createItemList2() {
        for (int i = 1; i <= 5; i++) {
            Item item = Item.builder()
                    .itemNm("테스트 상품" + i)
                    .price(10000 + i)
                    .stockNumber(100 + i)
                    .itemDetail("테스트 상품에 대한 상세설명" + i)
                    .itemSellStatus(ItemSellStatus.SELL)
                    .regTime(LocalDateTime.now())
                    .updateTime(LocalDateTime.now())
                    .build();
            itemRepository.save(item);
        }

        for (int i = 6; i <= 10; i++) {
            Item item = Item.builder()
                    .itemNm("테스트 상품" + i)
                    .price(10000 + i)
                    .stockNumber(100 + i)
                    .itemDetail("테스트 상품에 대한 상세설명" + i)
                    .itemSellStatus(ItemSellStatus.Sold_OUT)
                    .regTime(LocalDateTime.now())
                    .updateTime(LocalDateTime.now())
                    .build();
            itemRepository.save(item);
        }
    }

    //가게 조회시 사용할 것!!
    @Test
    @DisplayName("상품 querydsl 조회 테스트2")
    public void querydslTest2(){
        createItemList2();

        BooleanBuilder builder = new BooleanBuilder();
        QItem item = QItem.item;
        String itemDetail = "테스트";
        int price = 10002;
        String itemStatus = "SELL";


        //동적 쿼리
        builder.and(item.itemDetail.like("%" + itemDetail + "%"));
        builder.and(item.price.gt(price));

//        if(StringUtils.equals(itemStatus, ItemSellStatus.SELL)){
//            builder.and(item.itemSellStatus.eq(ItemSellStatus.SELL));
//        }

        Pageable pageable = PageRequest.of(1, 5);
        Page<Item> page = itemRepository.findAll(builder, pageable);

        List<Item> content = page.getContent();
        content.stream().forEach((e)->{
            System.out.println(e);
        });
    }
    @Test
    @DisplayName("JPQL 테스트")
    public void findByDetailTest(){
        createItemList();
        List<Item> detailList = itemRepository.findByDetail("1");
        detailList.forEach(System.out::println);
    }

    @Test
    @DisplayName("JPQL Native 테스트")
    public void findByDetailNativeTest(){
        createItemList();
        List<Item> detailList = itemRepository.findByDetailNative("1");
        detailList.forEach(System.out::println);
    }

    @Test
    @DisplayName("Querydsl 테스트")
    public void queryDslTest(){
        createItemList();
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QItem qItem = item;
        List<Item> itemList = queryFactory.selectFrom(item)
                .where(item.itemSellStatus.eq(ItemSellStatus.SELL))
                .where(item.itemDetail.like("%" + "1" + "%"))
                .orderBy(item.price.desc())
                .fetch();

        itemList.forEach(System.out::println);
    }

    @Test
    @DisplayName("가격에 대한 정렬 테스트")
    public void findByPriceLessThanOrderByPriceDesc(){
        createItemList();
        List<Item> priceLessThanOrderByPriceDesc = itemRepository.findByPriceLessThanOrderByPriceDesc(10005);
        priceLessThanOrderByPriceDesc.forEach(System.out::println);
    }

    @Test
    @DisplayName("상품명 또는 상품 상세 설명 조회 테스트")
    public void findByItemNmOrItemDetailTest(){
        createItemList();
        List<Item> itemList =
        itemRepository.findByItemNmOrItemDetail("테스트 상품1", "테스트 상품 상세 설명5");
        //itemList.forEach(item -> System.out.println(item));
        itemList.forEach(System.out::println);
    }


    @Test
    @DisplayName("상품명 조회 테스트")
    public void findByItemNmTest(){
        createItemList();
        List<Item> itemList =  itemRepository.findByItemNm("테스트 상품1");
        for (Item item : itemList) {
            System.out.println(item);
        }
    }

    @Test
    @DisplayName("상품 생성 테스트")
    public void createItemTest(){
        Item item = Item.builder()
                .itemNm("테스트 상품")
                .price(10000)
                .stockNumber(100)
                .itemDetail("테스트 상품에 대한 상세설명")
                .itemSellStatus(ItemSellStatus.SELL)
                .stockNumber(100)
                .regTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
        System.out.println("======================= item : " + item);
        Item savedItems = itemRepository.save(item);
        System.out.println("======================= savedItems : " + savedItems);
    }

    @Test
    @DisplayName("스트링부트 쿼리문 과제 테스트")
    public void findByStockNumberGreaterThanEqualAndItemNmLikeOrderByPriceAscTest(){
        this.createItemList();
        List<Item> byStockNumberGreaterThanEqualAndItemNmLikeOrderByPriceAscList =
                itemRepository.findByStockNumberGreaterThanEqualAndItemNmLikeOrderByPriceAsc(150, "%8%");
        byStockNumberGreaterThanEqualAndItemNmLikeOrderByPriceAscList.forEach(System.out::println);
    }


    @Test
    @DisplayName("스트링부트 JPQL 과제 테스트")
    public void findByStockNumberItemNmTest(){
        createItemList();
        List<Item> byStockNumberItemNmList = itemRepository.findByStockNumberItemNm(150,"8");
        byStockNumberItemNmList.forEach(System.out::println);
    }

    @Test
    @DisplayName("스트링부트 Native 과제 테스트")
    public void findByStockNumberAndItemNmNativeTest(){
        createItemList();
        List<Item> byStockNumberAndItemNmNativeList = itemRepository.findByStockNumberAndItemNmNative(150, "8");
        byStockNumberAndItemNmNativeList.forEach(System.out::println);
    }

    @Test
    @DisplayName("Querydsl 스트링부트 테스트")
    public void queryDslStockNumberTest(){
        createItemList();
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QItem qItem = item;
        List<Item> itemList = queryFactory.selectFrom(item)
                .where(item.stockNumber.goe(150))       //goe는 크거나 같다를 의미.
                .where(item.itemNm.like("%" + "8" + "%"))
                .orderBy(item.price.asc())
                .fetch();
        itemList.forEach(System.out::println);
    }


}