import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import UserProfile from './user-profile';
import Channel from './channel';
import ChannelMessage from './channel-message';
import ChannelMember from './channel-member';
import UserMessage from './user-message';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="user-profile/*" element={<UserProfile />} />
        <Route path="channel/*" element={<Channel />} />
        <Route path="channel-message/*" element={<ChannelMessage />} />
        <Route path="channel-member/*" element={<ChannelMember />} />
        <Route path="user-message/*" element={<UserMessage />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
