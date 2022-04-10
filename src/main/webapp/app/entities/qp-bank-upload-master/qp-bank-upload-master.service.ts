import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IQpBankUploadMaster } from 'app/shared/model/qp-bank-upload-master.model';

type EntityResponseType = HttpResponse<IQpBankUploadMaster>;
type EntityArrayResponseType = HttpResponse<IQpBankUploadMaster[]>;

@Injectable({ providedIn: 'root' })
export class QpBankUploadMasterService {
  public resourceUrl = SERVER_API_URL + 'api/qp-bank-upload-masters';

  constructor(protected http: HttpClient) {}

  create(qpBankUploadMaster: IQpBankUploadMaster): Observable<EntityResponseType> {
    return this.http.post<IQpBankUploadMaster>(this.resourceUrl, qpBankUploadMaster, { observe: 'response' });
  }

  update(qpBankUploadMaster: IQpBankUploadMaster): Observable<EntityResponseType> {
    return this.http.put<IQpBankUploadMaster>(this.resourceUrl, qpBankUploadMaster, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IQpBankUploadMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IQpBankUploadMaster[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
