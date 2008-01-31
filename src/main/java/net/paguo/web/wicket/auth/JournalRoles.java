package net.paguo.web.wicket.auth;

/**
 * Enum for easy set permission in code
 * TODO: awful data duplication. Think about it
 */
public enum JournalRoles {
    ROLE_VIEW_COMPLAINTS,
    ROLE_CHANGE_COMPLAINT,
    ROLE_DELETE_COMPLAINT,
    ROLE_OVERRIDE_COMPLAINT,
    ROLE_CREATE_COMPLAINT,
    ROLE_VIEW_PROBLEMS,
    ROLE_CHANGE_PROBLEM,
    ROLE_DELETE_PROBLEM,
    ROLE_OVERRIDE_PROBLEM,
    ROLE_CREATE_PROBLEM,
    ROLE_SUPERVISOR,
    ROLE_ADMIN
}
