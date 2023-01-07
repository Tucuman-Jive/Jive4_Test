import dayjs from 'dayjs';
import { IUserProfile } from 'app/shared/model/user-profile.model';

export interface IUserMessage {
  id?: number;
  recipientID?: number | null;
  message?: string | null;
  updatedAt?: string | null;
  userProfile?: IUserProfile | null;
}

export const defaultValue: Readonly<IUserMessage> = {};
