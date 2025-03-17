package com.mosque.masjedi.security;

import com.mosque.masjedi.dto.response.NoteResponse;
import com.mosque.masjedi.dto.response.LogbookResponse;
import com.mosque.masjedi.dto.response.StudentProgressResponse;
import com.mosque.masjedi.dto.response.UserResponse;
import com.mosque.masjedi.service.LogbookService;
import com.mosque.masjedi.service.NoteService;
import com.mosque.masjedi.service.StudentProgressService;
import com.mosque.masjedi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

/**
 * Utility class for common security operations used in @PreAuthorize
 * expressions
 */
@Component
@RequiredArgsConstructor
public class SecurityUtils {
    private final UserService userService;
    private final StudentProgressService studentProgressService;
    private final LogbookService logbookService;
    private final NoteService noteService;

    /**
     * Check if the current authenticated user is the student who owns the given
     * progress
     * 
     * @param progressId the ID of the student progress to check
     * @return true if the current user is the student who owns the progress
     */
    public boolean isStudentOwnerOfProgress(Long progressId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null) {
                return false;
            }

            Jwt jwt = (Jwt) authentication.getPrincipal();
            String username = jwt.getClaimAsString("preferred_username");
            UserResponse user = userService.getUserByUsername(username);

            StudentProgressResponse progress = studentProgressService.getStudentProgressById(progressId);

            return progress.getStudentId().equals(user.id());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if the current authenticated user is the student who owns the given
     * daily progress log
     * 
     * @param progressId the ID of the daily progress log to check
     * @return true if the current user is the student who owns the daily progress
     *         log
     */
    public boolean isStudentOwnerOfDailyProgress(Long progressId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null) {
                return false;
            }

            Jwt jwt = (Jwt) authentication.getPrincipal();
            String username = jwt.getClaimAsString("preferred_username");
            UserResponse user = userService.getUserByUsername(username);

            LogbookResponse progress = logbookService.getLogbookById(progressId);

            return progress.studentId().equals(user.id());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if the current authenticated user is the student who owns the given
     * note
     * 
     * @param noteId the ID of the note to check
     * @return true if the current user is the student who owns the note
     */
    public boolean isStudentOwnerOfNote(Long noteId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null) {
                return false;
            }

            Jwt jwt = (Jwt) authentication.getPrincipal();
            String username = jwt.getClaimAsString("preferred_username");
            UserResponse user = userService.getUserByUsername(username);

            NoteResponse note = noteService.getNoteById(noteId);

            return note.studentId().equals(user.id());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if the current authenticated user is the author of the given note
     * 
     * @param noteId the ID of the note to check
     * @return true if the current user is the author of the note
     */
    public boolean isAuthorOfNote(Long noteId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null) {
                return false;
            }

            Jwt jwt = (Jwt) authentication.getPrincipal();
            String username = jwt.getClaimAsString("preferred_username");
            UserResponse user = userService.getUserByUsername(username);

            NoteResponse note = noteService.getNoteById(noteId);

            return note.authorId().equals(user.id());
        } catch (Exception e) {
            return false;
        }
    }
}