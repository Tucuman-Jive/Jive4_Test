import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import UserMessage from './user-message';
import UserMessageDetail from './user-message-detail';
import UserMessageUpdate from './user-message-update';
import UserMessageDeleteDialog from './user-message-delete-dialog';

const UserMessageRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<UserMessage />} />
    <Route path="new" element={<UserMessageUpdate />} />
    <Route path=":id">
      <Route index element={<UserMessageDetail />} />
      <Route path="edit" element={<UserMessageUpdate />} />
      <Route path="delete" element={<UserMessageDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default UserMessageRoutes;
