import { IUserProfile } from 'app/shared/model/user-profile.model';
import { IChannel } from 'app/shared/model/channel.model';

export interface IChannelMember {
  id?: number;
  userProfile?: IUserProfile | null;
  channel?: IChannel | null;
}

export const defaultValue: Readonly<IChannelMember> = {};
