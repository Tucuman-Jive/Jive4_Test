import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/user-profile">
        <Translate contentKey="global.menu.entities.userProfile" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/channel">
        <Translate contentKey="global.menu.entities.channel" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/channel-message">
        <Translate contentKey="global.menu.entities.channelMessage" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/channel-member">
        <Translate contentKey="global.menu.entities.channelMember" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/user-message">
        <Translate contentKey="global.menu.entities.userMessage" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
