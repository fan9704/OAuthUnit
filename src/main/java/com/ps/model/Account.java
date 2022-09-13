package com.ps.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by samchu on 2017/2/14.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class) //加這行 CreatedBy 才會生效
public class Account {
    @Id
    private String accountid;

    @NotNull
//    @Size(min = 8, max = 255, message = "Username have to be grater than 8 characters")
    @Column(unique = true)
    private String username;

    @NotNull
    private String email;

    @NotNull
//    @Size(min = 8, max = 255, message = "Password have to be grater than 8 characters")
    private String password;

    @NotNull
    private boolean enabled = true;

    @NotNull
    private boolean credentialsexpired = false;

    @NotNull
    private boolean expired = false;

    @NotNull
    private boolean locked = false;

//    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinTable(name = "AccountRole", joinColumns = @JoinColumn(name = "accountid", referencedColumnName = "accountid"),
//            inverseJoinColumns = @JoinColumn(name = "roleid", referencedColumnName = "roleid"))
//    private List<Role> roles;

    @CreatedDate
    @Column(name = "createddate")
    private Date createddate;
    @CreatedBy
    @Column(name = "createdby")
    private String createdby;
    @LastModifiedDate
    @Column(name = "lastmodifieddate")
    private Date lastmodifieddate;
    @LastModifiedBy
    @Column(name = "lastmodifiedby")
    private String lastmodifiedby;
}
