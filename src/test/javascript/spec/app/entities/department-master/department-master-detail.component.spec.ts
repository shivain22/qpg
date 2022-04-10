import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { QpgTestModule } from '../../../test.module';
import { DepartmentMasterDetailComponent } from 'app/entities/department-master/department-master-detail.component';
import { DepartmentMaster } from 'app/shared/model/department-master.model';

describe('Component Tests', () => {
  describe('DepartmentMaster Management Detail Component', () => {
    let comp: DepartmentMasterDetailComponent;
    let fixture: ComponentFixture<DepartmentMasterDetailComponent>;
    const route = ({ data: of({ departmentMaster: new DepartmentMaster(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [QpgTestModule],
        declarations: [DepartmentMasterDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DepartmentMasterDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DepartmentMasterDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load departmentMaster on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.departmentMaster).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
