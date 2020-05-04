package jpa.jpql.domain.dto;

public class MemberDTO {

    private String userName;
    private int age;

    public MemberDTO(String userName, int age) {
        this.userName = userName;
        this.age = age;
    }
}
