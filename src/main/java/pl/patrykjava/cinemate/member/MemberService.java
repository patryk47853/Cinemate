package pl.patrykjava.cinemate.member;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.patrykjava.cinemate.comment.CommentDao;
import pl.patrykjava.cinemate.exception.DuplicateResourceException;
import pl.patrykjava.cinemate.exception.RequestValidationException;
import pl.patrykjava.cinemate.exception.ResourceNotFoundException;
import pl.patrykjava.cinemate.movie.Movie;
import pl.patrykjava.cinemate.movie.MovieRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MemberService {
    private final MemberDao memberDao;
    private final MovieRepository movieRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemberDtoMapper memberDtoMapper;
    private final CommentDao commentDao;

    public MemberService(@Qualifier("jpa") MemberDao memberDao, MovieRepository movieRepository, PasswordEncoder passwordEncoder, MemberDtoMapper memberDtoMapper, CommentDao commentDao) {
        this.memberDao = memberDao;
        this.movieRepository = movieRepository;
        this.passwordEncoder = passwordEncoder;
        this.memberDtoMapper = memberDtoMapper;
        this.commentDao = commentDao;
    }

    public MemberDto getMember(Long id) {
        return memberDao.selectMemberById(id)
                .map(memberDtoMapper)
                .orElseThrow(() -> new ResourceNotFoundException("No member with ID: " + id + " has been found."));
    }

    public MemberDto getMember(String username) {
        return memberDao.selectMemberByUsername(username)
                .map(memberDtoMapper)
                .orElseThrow(() -> new ResourceNotFoundException("No member with username: " + username + " has been found."));
    }

    public List<MemberDto> getAllMembers() {
        return memberDao.selectAllMembers()
                .stream()
                .map(memberDtoMapper)
                .collect(Collectors.toList());
    }

    public void addMember(MemberRegistrationRequest memberRegistrationRequest) {
        String email = memberRegistrationRequest.email();
        if(memberDao.existsMemberWithEmail(email)) {
            throw new DuplicateResourceException("Email: " + email + " is already taken.");
        }

        String username = memberRegistrationRequest.username();
        if(memberDao.existsMemberWithUsername(username)) {
            throw new DuplicateResourceException("Username: " + username + " is already taken.");
        }

        Member member = new Member(
                memberRegistrationRequest.username(),
                memberRegistrationRequest.email(),
                passwordEncoder.encode(memberRegistrationRequest.password()),
                generateImgUrl()
        );

        member.getRoles().add(Role.ROLE_USER);

        memberDao.insertMember(member);
    }

    public void deleteMemberById(Long id) {
        if (!memberDao.existsMemberWithId(id)) throw new ResourceNotFoundException("Member with ID: " + id + " not found.");

        memberDao.deleteMemberById(id);
    }

    public void updateMember(Long id, MemberUpdateRequest request) {
        Member member = memberDao.selectMemberById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No user with such ID: " + id + " has been found."));

        boolean changed = false;

        if(request.username() != null && !request.username().equals(member.getUsername())) {
            if(memberDao.existsMemberWithUsername(request.username())) {
                throw new DuplicateResourceException("Username: " + request.username() + " is already taken.");
            }

            member.setUsername(request.username());
            changed = true;
        }

        if(request.email() != null && !request.email().equals(member.getEmail())) {
            if(memberDao.existsMemberWithEmail(request.email())) {
                throw new DuplicateResourceException("Email: " + request.email() + " is already taken.");
            }

            member.setEmail(request.email());
            changed = true;
        }

        if(request.password() != null && !request.password().equals(member.getPassword())) {
            member.setPassword(request.password());
            changed = true;
        }

        if(!changed) throw new RequestValidationException("No changes were made.");

        memberDao.updateMember(member);
    }

    public void addMovieToFavorites(Long memberId, Long movieId) {
        Optional<Member> memberOptional = memberDao.selectMemberById(memberId);
        Optional<Movie> movieOptional = movieRepository.findById(movieId);

        if (memberOptional.isPresent() && movieOptional.isPresent()) {
            Member member = memberOptional.get();
            Movie movie = movieOptional.get();
            if (!member.getFavoriteMovies().contains(movie)) {
                member.getFavoriteMovies().add(movie);
                memberDao.updateMember(member);
            } else {
                throw new DuplicateResourceException("Move is already added to favorites");
            }
        } else {
            throw new RuntimeException("Member or Movie not found");
        }
    }

    public void removeMovieFromFavorites(Long memberId, Long movieId) {
        Optional<Member> memberOptional = memberDao.selectMemberById(memberId);
        Optional<Movie> movieOptional = movieRepository.findById(movieId);

        if (memberOptional.isPresent() && movieOptional.isPresent()) {
            Member member = memberOptional.get();
            Movie movie = movieOptional.get();
            if (member.getFavoriteMovies().contains(movie)) {
                member.getFavoriteMovies().remove(movie);
                memberDao.updateMember(member);
            } else {
                throw new ResourceNotFoundException("Movie is not in the favorites list");
            }
        } else {
            throw new RuntimeException("Member or Movie not found");
        }
    }

    private String generateImgUrl() {
        int randomNumber = (int) (Math.random() * 71); // Generate random number between 0 and 70
        return "https://i.pravatar.cc/300?img=" + randomNumber;
    }
}
