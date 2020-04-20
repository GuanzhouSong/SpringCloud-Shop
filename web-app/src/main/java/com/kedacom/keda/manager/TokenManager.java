package com.kedacom.keda.manager;

import com.kedacom.keda.model.TokenModel;

/**
 * Interface to operate token.
 */
public interface TokenManager {

    /**
     * Create a token for a particular user.
     * @param userId user id
     * @return generated token
     */
    TokenModel createToken(long userId);

    /**
     * Check whether token is effective
     * @param model token
     * @return true or false
     */
    boolean checkToken(TokenModel model);

    /**
     * Analyze token from string
     * @param authentication authenticated string
     * @return
     */
    TokenModel getToken(String authentication);

    /**
     * Delete token
     * @param userId user id id
     */
    void deleteToken(long userId);

}
