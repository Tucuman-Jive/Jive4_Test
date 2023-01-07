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
import { IUserMessage } from 'app/shared/model/user-message.model';
import { getEntity, updateEntity, createEntity, reset } from './user-message.reducer';

export const UserMessageUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const userProfiles = useAppSelector(state => state.userProfile.entities);
  const userMessageEntity = useAppSelector(state => state.userMessage.entity);
  const loading = useAppSelector(state => state.userMessage.loading);
  const updating = useAppSelector(state => state.userMessage.updating);
  const updateSuccess = useAppSelector(state => state.userMessage.updateSuccess);

  const handleClose = () => {
    navigate('/user-message');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getUserProfiles({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.updatedAt = convertDateTimeToServer(values.updatedAt);

    const entity = {
      ...userMessageEntity,
      ...values,
      userProfile: userProfiles.find(it => it.id.toString() === values.userProfile.toString()),
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
          ...userMessageEntity,
          updatedAt: convertDateTimeFromServer(userMessageEntity.updatedAt),
          userProfile: userMessageEntity?.userProfile?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jiveApp.userMessage.home.createOrEditLabel" data-cy="UserMessageCreateUpdateHeading">
            <Translate contentKey="jiveApp.userMessage.home.createOrEditLabel">Create or edit a UserMessage</Translate>
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
                  id="user-message-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('jiveApp.userMessage.recipientID')}
                id="user-message-recipientID"
                name="recipientID"
                data-cy="recipientID"
                type="text"
              />
              <ValidatedField
                label={translate('jiveApp.userMessage.message')}
                id="user-message-message"
                name="message"
                data-cy="message"
                type="text"
              />
              <ValidatedField
                label={translate('jiveApp.userMessage.updatedAt')}
                id="user-message-updatedAt"
                name="updatedAt"
                data-cy="updatedAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="user-message-userProfile"
                name="userProfile"
                data-cy="userProfile"
                label={translate('jiveApp.userMessage.userProfile')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/user-message" replace color="info">
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

export default UserMessageUpdate;
