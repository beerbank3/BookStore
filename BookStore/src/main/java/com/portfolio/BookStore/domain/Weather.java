//package com.portfolio.Seoul_bus.domain;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Getter @Setter
//public class Weather {
//
//    @Id @GeneratedValue
//    @Column(name ="weather_id")
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "city_id")
//    private City city;
//
//    @OneToMany(mappedBy = "weather", cascade = CascadeType.ALL)
//    private List<Category> categories = new ArrayList<>(); //자료구분코드
//
//    private LocalDateTime Date; //날짜
//}
