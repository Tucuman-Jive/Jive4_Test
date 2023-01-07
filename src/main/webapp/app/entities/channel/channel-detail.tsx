import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './channel.reducer';

export const ChannelDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const channelEntity = useAppSelector(state => state.channel.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="channelDetailsHeading">
          <Translate contentKey="jiveApp.channel.detail.title">Channel</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{channelEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="jiveApp.channel.name">Name</Translate>
            </span>
          </dt>
          <dd>{channelEntity.name}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="jiveApp.channel.description">Description</Translate>
            </span>
          </dt>
          <dd>{channelEntity.description}</dd>
          <dt>
            <span id="updatedAt">
              <Translate contentKey="jiveApp.channel.updatedAt">Updated At</Translate>
            </span>
          </dt>
          <dd>{channelEntity.updatedAt ? <TextFormat value={channelEntity.updatedAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
        </dl>
        <Button tag={Link} to="/channel" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/channel/${channelEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ChannelDetail;
