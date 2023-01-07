import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './user-profile.reducer';

export const UserProfileDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const userProfileEntity = useAppSelector(state => state.userProfile.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="userProfileDetailsHeading">
          <Translate contentKey="jiveApp.userProfile.detail.title">UserProfile</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{userProfileEntity.id}</dd>
          <dt>
            <span id="displayName">
              <Translate contentKey="jiveApp.userProfile.displayName">Display Name</Translate>
            </span>
          </dt>
          <dd>{userProfileEntity.displayName}</dd>
          <dt>
            <span id="profileImage">
              <Translate contentKey="jiveApp.userProfile.profileImage">Profile Image</Translate>
            </span>
          </dt>
          <dd>
            {userProfileEntity.profileImage ? (
              <div>
                {userProfileEntity.profileImageContentType ? (
                  <a onClick={openFile(userProfileEntity.profileImageContentType, userProfileEntity.profileImage)}>
                    <img
                      src={`data:${userProfileEntity.profileImageContentType};base64,${userProfileEntity.profileImage}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                ) : null}
                <span>
                  {userProfileEntity.profileImageContentType}, {byteSize(userProfileEntity.profileImage)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="jiveApp.userProfile.user">User</Translate>
          </dt>
          <dd>{userProfileEntity.user ? userProfileEntity.user.login : ''}</dd>
        </dl>
        <Button tag={Link} to="/user-profile" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/user-profile/${userProfileEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default UserProfileDetail;
