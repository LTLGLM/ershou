// package com.ershou.ershou.controller;
//
// import org.springframework.security.access.prepost.PreAuthorize;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
//
// /**
//  * 测试授权失败
//  */
// @RestController
// public class HelloContorller {
//
//     @RequestMapping("/hello")
//     @PreAuthorize("hasAuthority('usermanager:vipuser:list')")
//     public String hello(){
//         System.out.println(
//                 "hello"
//         );
//         return "hello";
//     }
// }
