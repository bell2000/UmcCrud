package com.example.umccrud;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
//requiredargsconstructor쓰려다 repository를 final로 선언하지 않으면 안된다는 것을 알게됨.
public class MemoController {
    @Autowired
    private MemoRepository memoRepository;

    //Spring Data JPA에서 Pageable을 사용하여 페이징 및 정렬 기능을 지원하는 것은 Spring Data JPA의 내장된 기능 중 하나입니다.
    // Pageable 인터페이스는 페이징과 관련된 정보를 전달하는 데 사용되며, Spring Data JPA는 이를 이용하여 데이터베이스에서 데이터를
    // 페이지 단위로 가져오고 정렬하는 쿼리를 생성합니다.
    //
    // 다음과 같은 메서드는 Pageable을 사용하여 페이징된 결과를 반환하는 메서드.
    @GetMapping("/memos")
    public String listMemos(@RequestParam(required = false) String keyword, @RequestParam(required = false, defaultValue = "desc") String order,
                            @RequestParam(defaultValue = "1") int page,
                            @RequestParam(defaultValue = "5") int size,
                            Model model) {
        Page<Memo> memos;
        Pageable pageable = PageRequest.of(page - 1, size);
        //일반적으로 많은 웹 애플리케이션에서는 페이지 번호가 1부터 시작하는 것이 일반적. 그래서 page - 1과 같이 처리하여
        // 클라이언트가 전달한 페이지 번호를 1부터 시작하는 것처럼 인식하게 된다.
        //
        //예를 들어, 클라이언트에서 "1페이지"를 요청하면, 실제로는 PageRequest.of(0, size)와 같이 처리하여 데이터베이스에서는 0번째 페이지를 가져오게 된다.
        // 이는 대부분의 페이징 라이브러리 및 데이터베이스에서 사용하는 관행
        //이로써 클라이언트와의 혼동을 방지하고 페이징 처리를 보다 일관적이고 직관적으로 구현가능해진다.

        if (keyword != null) {
            if ("asc".equals(order)) {
                memos = memoRepository.findByTitleContainingOrderByCreatedAtAsc(keyword,pageable);
            } else {
                memos = memoRepository.findByTitleContainingOrderByCreatedAtDesc(keyword,pageable);
            }
        } else {
            if ("asc".equals(order)) {
                pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Order.asc("createdAt")));
                memos = memoRepository.findAll(pageable);
            } else {
                pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Order.desc("createdAt")));
                memos = memoRepository.findAll(pageable);
            }
        }
        model.addAttribute("memos", memos);
        model.addAttribute("keyword", keyword);
        model.addAttribute("order", order);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("totalPages", memos.getTotalPages());
        return "memos/list";
    }

    @GetMapping("/memos/new")
    public String createMemoForm(Model model) {
        model.addAttribute("memo", new Memo());
        return "memos/create";
    }


    //save 메소드:
    // 메모를 저장 또는 업데이트하는 메소드.
    //사용된 곳: 새로운 메모를 생성하거나, 기존 메모를 업데이트할 때 사용된다.
    //crud기준 create와 update에 사용된다고 생각하면 편하다.


    @PostMapping("/memos/new")
    public String createMemo(@ModelAttribute Memo memo) {
        memoRepository.save(memo);
        return "redirect:/memos";
    }

    @GetMapping("/memos/edit/{id}")
    public String editMemoForm(@PathVariable Long id, Model model) {
        Memo memo = memoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid memo id"));
        model.addAttribute("memo", memo);
        return "memos/edit";
    }

    @PostMapping("/memos/edit/{id}")
    public String editMemo(@PathVariable Long id, @ModelAttribute Memo updatedMemo) {
        Memo memo = memoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid memo id"));
        memo.setTitle(updatedMemo.getTitle());
        memo.setContent(updatedMemo.getContent());
        memoRepository.save(memo);
        return "redirect:/memos";
    }

    //deleteById 메소드:
    //주어진 ID에 해당하는 메모를 삭제
    @GetMapping("/memos/delete/{id}")
    public String deleteMemo(@PathVariable Long id) {
        memoRepository.deleteById(id);
        return "redirect:/memos";
    }



    @PostMapping("/memos/deleteAll")
    public String deleteAllMemos() {
        memoRepository.deleteAll();
        return "redirect:/memos";
    }
}

