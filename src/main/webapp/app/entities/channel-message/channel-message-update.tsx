import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUserProfile } from 'app/shared/model/user-profile.model';
import { getEntities as getUserProfiles } from 'app/entities/user-profile/user-profile.reducer';
import { IChannel } from 'app/shared/model/channel.model';
import { getEntities as getChannels } from 'app/entities/channel/channel.reducer';
import { IChannelMessage } from 'app/shared/model/channel-message.model';
import { getEntity, updateEntity, createEntity, reset } from './channel-message.reducer';

export const ChannelMessageUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const userProfiles = useAppSelector(state => state.userProfile.entities);
  const channels = useAppSelector(state => state.channel.entities);
  const channelMessageEntity = useAppSelector(state => state.channelMessage.entity);
  const loading = useAppSelector(state => state.channelMessage.loading);
  const updating = useAppSelector(state => state.channelMessage.updating);
  const updateSuccess = useAppSelector(state => state.channelMessage.updateSuccess);

  const handleClose = () => {
    navigate('/channel-message');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getUserProfiles({}));
    dispatch(getChannels({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.updatedAt = convertDateTimeToServer(values.updatedAt);

    const entity = {
      ...channelMessageEntity,
      ...values,
      userProfile: userProfiles.find(it => it.id.toString() === values.userProfile.toString()),
      channel: channels.find(it => it.id.toString() === values.channel.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          updatedAt: displayDefaultDateTime(),
        }
      : {
          ...channelMessageEntity,
          updatedAt: convertDateTimeFromServer(channelMessageEntity.updatedAt),
          userProfile: channelMessageEntity?.userProfile?.id,
          channel: channelMessageEntity?.channel?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jiveApp.channelMessage.home.createOrEditLabel" data-cy="ChannelMessageCreateUpdateHeading">
            <Translate contentKey="jiveApp.channelMessage.home.createOrEditLabel">Create or edit a ChannelMessage</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="channel-message-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('jiveApp.channelMessage.message')}
                id="channel-message-message"
                name="message"
                data-cy="message"
                type="text"
              />
              <ValidatedField
                label={translate('jiveApp.channelMessage.updatedAt')}
                id="channel-message-updatedAt"
                name="updatedAt"
                data-cy="updatedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="channel-message-userProfile"
                name="userProfile"
                data-cy="userProfile"
                label={translate('jiveApp.channelMessage.userProfile')}
                type="select"
              >
                <option value="" key="0" />
                {userProfiles
                  ? userProfiles.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="channel-message-channel"
                name="channel"
                data-cy="channel"
                label={translate('jiveApp.channelMessage.channel')}
                type="select"
              >
                <option value="" key="0" />
                {channels
                  ? channels.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/channel-message" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ChannelMessageUpdate;
