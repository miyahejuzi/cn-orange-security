package cn.orange.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Callable;

/**
 * Callable DeferredResult
 *
 * @author : kz
 * @date : 2019/7/23
 */
@RestController
@RequestMapping("/async")
public class AsyncController {

    private static DeferredResult<String> deferredResult;

    @GetMapping
    public Callable<String> asyncQuery() {
        return () -> "success";
    }

    @GetMapping("/deferred")
    public DeferredResult<String> deferred() {
        deferredResult = new DeferredResult<>();
        return deferredResult;
    }

    /**
     * 只有当被 set 的时候, 才能将值返回
     */
    @GetMapping("/set")
    public void setValue() {
        deferredResult.setResult("success");
    }

}
