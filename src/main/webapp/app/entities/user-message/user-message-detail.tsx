import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './user-message.reducer';

export const UserMessageDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const userMessageEntity = useAppSelector(state => state.userMessage.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="userMessageDetailsHeading">
          <Translate contentKey="jiveApp.userMessage.detail.title">UserMessage</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{userMessageEntity.id}</dd>
          <dt>
            <span id="recipientID">
              <Translate contentKey="jiveApp.userMessage.recipientID">Recipient ID</Translate>
            </span>
          </dt>
          <dd>{userMessageEntity.recipientID}</dd>
          <dt>
            <span id="message">
              <Translate contentKey="jiveApp.userMessage.message">Message</Translate>
            </span>
          </dt>
          <dd>{userMessageEntity.message}</dd>
          <dt>
            <span id="updatedAt">
              <Translate contentKey="jiveApp.userMessage.updatedAt">Updated At</Translate>
            </span>
          </dt>
          <dd>
            {userMessageEntity.updatedAt ? <TextFormat value={userMessageEntity.updatedAt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="jiveApp.userMessage.userProfile">User Profile</Translate>
          </dt>
          <dd>{userMessageEntity.userProfile ? userMessageEntity.userProfile.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/user-message" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/user-message/${userMessageEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default UserMessageDetail;
