import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IChannelMessage } from 'app/shared/model/channel-message.model';
import { getEntities } from './channel-message.reducer';

export const ChannelMessage = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const channelMessageList = useAppSelector(state => state.channelMessage.entities);
  const loading = useAppSelector(state => state.channelMessage.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="channel-message-heading" data-cy="ChannelMessageHeading">
        <Translate contentKey="jiveApp.channelMessage.home.title">Channel Messages</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jiveApp.channelMessage.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/channel-message/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jiveApp.channelMessage.home.createLabel">Create new Channel Message</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {channelMessageList && channelMessageList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="jiveApp.channelMessage.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="jiveApp.channelMessage.message">Message</Translate>
                </th>
                <th>
                  <Translate contentKey="jiveApp.channelMessage.updatedAt">Updated At</Translate>
                </th>
                <th>
                  <Translate contentKey="jiveApp.channelMessage.userProfile">User Profile</Translate>
                </th>
                <th>
                  <Translate contentKey="jiveApp.channelMessage.channel">Channel</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {channelMessageList.map((channelMessage, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/channel-message/${channelMessage.id}`} color="link" size="sm">
                      {channelMessage.id}
                    </Button>
                  </td>
                  <td>{channelMessage.message}</td>
                  <td>
                    {channelMessage.updatedAt ? <TextFormat type="date" value={channelMessage.updatedAt} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {channelMessage.userProfile ? (
                      <Link to={`/user-profile/${channelMessage.userProfile.id}`}>{channelMessage.userProfile.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {channelMessage.channel ? <Link to={`/channel/${channelMessage.channel.id}`}>{channelMessage.channel.id}</Link> : ''}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/channel-message/${channelMessage.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/channel-message/${channelMessage.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/channel-message/${channelMessage.id}/delete`}
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
              <Translate contentKey="jiveApp.channelMessage.home.notFound">No Channel Messages found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default ChannelMessage;
