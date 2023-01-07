import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUserMessage } from 'app/shared/model/user-message.model';
import { getEntities } from './user-message.reducer';

export const UserMessage = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const userMessageList = useAppSelector(state => state.userMessage.entities);
  const loading = useAppSelector(state => state.userMessage.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="user-message-heading" data-cy="UserMessageHeading">
        <Translate contentKey="jiveApp.userMessage.home.title">User Messages</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jiveApp.userMessage.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/user-message/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jiveApp.userMessage.home.createLabel">Create new User Message</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {userMessageList && userMessageList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="jiveApp.userMessage.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="jiveApp.userMessage.recipientID">Recipient ID</Translate>
                </th>
                <th>
                  <Translate contentKey="jiveApp.userMessage.message">Message</Translate>
                </th>
                <th>
                  <Translate contentKey="jiveApp.userMessage.updatedAt">Updated At</Translate>
                </th>
                <th>
                  <Translate contentKey="jiveApp.userMessage.userProfile">User Profile</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {userMessageList.map((userMessage, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/user-message/${userMessage.id}`} color="link" size="sm">
                      {userMessage.id}
                    </Button>
                  </td>
                  <td>{userMessage.recipientID}</td>
                  <td>{userMessage.message}</td>
                  <td>
                    {userMessage.updatedAt ? <TextFormat type="date" value={userMessage.updatedAt} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {userMessage.userProfile ? (
                      <Link to={`/user-profile/${userMessage.userProfile.id}`}>{userMessage.userProfile.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/user-message/${userMessage.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/user-message/${userMessage.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/user-message/${userMessage.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="jiveApp.userMessage.home.notFound">No User Messages found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default UserMessage;
