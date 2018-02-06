/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { FinapplicationTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ContaFinappDetailComponent } from '../../../../../../main/webapp/app/entities/conta/conta-finapp-detail.component';
import { ContaFinappService } from '../../../../../../main/webapp/app/entities/conta/conta-finapp.service';
import { ContaFinapp } from '../../../../../../main/webapp/app/entities/conta/conta-finapp.model';

describe('Component Tests', () => {

    describe('ContaFinapp Management Detail Component', () => {
        let comp: ContaFinappDetailComponent;
        let fixture: ComponentFixture<ContaFinappDetailComponent>;
        let service: ContaFinappService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinapplicationTestModule],
                declarations: [ContaFinappDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ContaFinappService,
                    JhiEventManager
                ]
            }).overrideTemplate(ContaFinappDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ContaFinappDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ContaFinappService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ContaFinapp(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.conta).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
