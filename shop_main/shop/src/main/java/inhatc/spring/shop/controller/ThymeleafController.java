package inhatc.spring.shop.controller;

import inhatc.spring.shop.dto.ItemDto;
import inhatc.spring.shop.dto.ParamDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.awt.*;

@Controller
@Slf4j
//@RequestMapping("/thymeleaf") 할결우 밑에 /thymeleaf를 쓰지 않아도 됨.
public class ThymeleafController {

    @GetMapping("/thymeleaf/ex4")
    public String ex4(){
        return "thymeleaf/ex4";
    }

    @GetMapping("/thymeleaf/ex3")
    public String ex3(ParamDto paramDto, Model model){
        log.info("==========================> paramDto : " + paramDto);
        model.addAttribute("dto", paramDto);
        return "thymeleaf/ex3";
    }

    @GetMapping("/thymeleaf/ex2")
    public String ex2(){
        return "thymeleaf/ex2";
    }

    @GetMapping("/thymeleaf/ex1")
    public String ex1(Model model) {
//        Point p = new Point(10, 20);
//        model.addAttribute("data", p);

        ItemDto itemDto = ItemDto.builder()
                .itemNm("테스트 상품")
                .itemSellStatus("SELL")
                .price(10000)
                .itemDetail("상품 상세 설명")
                .stockNumber(200)
                .build();
        model.addAttribute("itemDto", itemDto);
        return "thymeleaf/ex1";
    }
}
