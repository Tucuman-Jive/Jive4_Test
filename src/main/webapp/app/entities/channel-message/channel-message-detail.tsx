import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './channel-message.reducer';

export const ChannelMessageDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const channelMessageEntity = useAppSelector(state => state.channelMessage.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="channelMessageDetailsHeading">
          <Translate contentKey="jiveApp.channelMessage.detail.title">ChannelMessage</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{channelMessageEntity.id}</dd>
          <dt>
            <span id="message">
              <Translate contentKey="jiveApp.channelMessage.message">Message</Translate>
            </span>
          </dt>
          <dd>{channelMessageEntity.message}</dd>
          <dt>
            <span id="updatedAt">
              <Translate contentKey="jiveApp.channelMessage.updatedAt">Updated At</Translate>
            </span>
          </dt>
          <dd>
            {channelMessageEntity.updatedAt ? (
              <TextFormat value={channelMessageEntity.updatedAt} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="jiveApp.channelMessage.userProfile">User Profile</Translate>
          </dt>
          <dd>{channelMessageEntity.userProfile ? channelMessageEntity.userProfile.id : ''}</dd>
          <dt>
            <Translate contentKey="jiveApp.channelMessage.channel">Channel</Translate>
          </dt>
          <dd>{channelMessageEntity.channel ? channelMessageEntity.channel.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/channel-message" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/channel-message/${channelMessageEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ChannelMessageDetail;
