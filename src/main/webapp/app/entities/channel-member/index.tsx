import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ChannelMember from './channel-member';
import ChannelMemberDetail from './channel-member-detail';
import ChannelMemberUpdate from './channel-member-update';
import ChannelMemberDeleteDialog from './channel-member-delete-dialog';

const ChannelMemberRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ChannelMember />} />
    <Route path="new" element={<ChannelMemberUpdate />} />
    <Route path=":id">
      <Route index element={<ChannelMemberDetail />} />
      <Route path="edit" element={<ChannelMemberUpdate />} />
      <Route path="delete" element={<ChannelMemberDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ChannelMemberRoutes;
