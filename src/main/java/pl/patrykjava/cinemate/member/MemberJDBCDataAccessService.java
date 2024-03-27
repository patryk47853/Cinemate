package pl.patrykjava.cinemate.member;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jdbc")
public class MemberJDBCDataAccessService implements MemberDao {

    private final JdbcTemplate jdbcTemplate;
    private final MemberRowMapper memberRowMapper;

    public MemberJDBCDataAccessService(JdbcTemplate jdbcTemplate,
                                       MemberRowMapper memberRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.memberRowMapper = memberRowMapper;
    }

    @Override
    public List<Member> selectAllMembers() {
        var sql = """
                SELECT id, username, email, password
                FROM member
                LIMIT 1000
                """;

        return jdbcTemplate.query(sql, memberRowMapper);
    }

    @Override
    public Optional<Member> selectMemberById(Long id) {
        var sql = """
                SELECT id, username, email, password
                FROM member
                WHERE id = ?
                """;

        return jdbcTemplate.query(sql, memberRowMapper, id)
                .stream()
                .findFirst();
    }

    @Override
    public Optional<Member> selectMemberByUsername(String username) {
        var sql = """
                SELECT id, username, email, password
                FROM member
                WHERE username = ?
                """;

        return jdbcTemplate.query(sql, memberRowMapper, username)
                .stream()
                .findFirst();
    }

    @Override
    public Optional<Member> selectMemberByEmail(String email) {
        var sql = """
                SELECT id, username, email, password
                FROM member
                WHERE email = ?
                """;

        return jdbcTemplate.query(sql, memberRowMapper, email)
                .stream()
                .findFirst();
    }

    @Override
    public void insertMember(Member member) {
        var sql = """
                INSERT INTO member(username, email, password)
                VALUES (?, ?, ?)
                """;

        int result = jdbcTemplate.update(
                sql,
                member.getUsername(),
                member.getEmail(),
                member.getPassword()
        );

        System.out.println("insertMember method result: " + result);
    }

    @Override
    public boolean existsMemberWithEmail(String email) {
        var sql = """
                SELECT count(id)
                FROM member
                WHERE email = ?
                """;

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    @Override
    public boolean existsMemberWithId(Long id) {
        var sql = """
                SELECT count(id)
                FROM member
                WHERE id = ?
                """;

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    @Override
    public boolean existsMemberWithUsername(String username) {
        var sql = """
                SELECT count(id)
                FROM member
                WHERE username = ?
                """;

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username);
        return count != null && count > 0;
    }

    @Override
    public void deleteMemberById(Long memberId) {
        var sql = """
                DELETE
                FROM member
                WHERE id = ?
                """;
        int result = jdbcTemplate.update(sql, memberId);
        System.out.println("deleteMemberById method result: " + result);
    }

    @Override
    public void updateMember(Member update) {
        if (update.getUsername() != null) {
            String sql = "UPDATE member SET username = ? WHERE id = ?";
            int result = jdbcTemplate.update(
                    sql,
                    update.getUsername(),
                    update.getId()
            );
            System.out.println("updateMember method result: " + result);
        }
        if (update.getEmail() != null) {
            String sql = "UPDATE member SET email = ? WHERE id = ?";
            int result = jdbcTemplate.update(
                    sql,
                    update.getEmail(),
                    update.getId()
            );
            System.out.println("update customer age result = " + result);
        }
    }

//    @Override
//    public Optional<Member> selectMemberByEmail(String email) {
//        var sql = """
//                SELECT id, username, email, password
//                FROM member
//                WHERE email = ?
//                """;
//
//        return jdbcTemplate.query(sql, memberRowMapper, email)
//                .stream()
//                .findFirst();
//    }
}
