import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IConfigMaster } from 'app/shared/model/config-master.model';

type EntityResponseType = HttpResponse<IConfigMaster>;
type EntityArrayResponseType = HttpResponse<IConfigMaster[]>;

@Injectable({ providedIn: 'root' })
export class ConfigMasterService {
  public resourceUrl = SERVER_API_URL + 'api/config-masters';

  constructor(protected http: HttpClient) {}

  create(configMaster: IConfigMaster): Observable<EntityResponseType> {
    return this.http.post<IConfigMaster>(this.resourceUrl, configMaster, { observe: 'response' });
  }

  update(configMaster: IConfigMaster): Observable<EntityResponseType> {
    return this.http.put<IConfigMaster>(this.resourceUrl, configMaster, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IConfigMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IConfigMaster[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
