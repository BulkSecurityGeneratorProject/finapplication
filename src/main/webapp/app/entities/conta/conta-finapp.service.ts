import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { ContaFinapp } from './conta-finapp.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ContaFinappService {

    private resourceUrl = SERVER_API_URL + 'api/contas';

    constructor(private http: Http) { }

    create(conta: ContaFinapp): Observable<ContaFinapp> {
        const copy = this.convert(conta);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(conta: ContaFinapp): Observable<ContaFinapp> {
        const copy = this.convert(conta);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<ContaFinapp> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(conta: ContaFinapp): ContaFinapp {
        const copy: ContaFinapp = Object.assign({}, conta);
        return copy;
    }
}
