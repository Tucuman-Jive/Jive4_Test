import dayjs from 'dayjs';
import { IUserProfile } from 'app/shared/model/user-profile.model';
import { IChannel } from 'app/shared/model/channel.model';

export interface IChannelMessage {
  id?: number;
  message?: string | null;
  updatedAt?: string | null;
  userProfile?: IUserProfile | null;
  channel?: IChannel | null;
}

export const defaultValue: Readonly<IChannelMessage> = {};
