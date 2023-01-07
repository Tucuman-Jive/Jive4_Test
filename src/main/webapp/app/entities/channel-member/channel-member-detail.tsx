import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './channel-member.reducer';

export const ChannelMemberDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const channelMemberEntity = useAppSelector(state => state.channelMember.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="channelMemberDetailsHeading">
          <Translate contentKey="jiveApp.channelMember.detail.title">ChannelMember</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{channelMemberEntity.id}</dd>
          <dt>
            <Translate contentKey="jiveApp.channelMember.userProfile">User Profile</Translate>
          </dt>
          <dd>{channelMemberEntity.userProfile ? channelMemberEntity.userProfile.id : ''}</dd>
          <dt>
            <Translate contentKey="jiveApp.channelMember.channel">Channel</Translate>
          </dt>
          <dd>{channelMemberEntity.channel ? channelMemberEntity.channel.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/channel-member" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/channel-member/${channelMemberEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ChannelMemberDetail;
